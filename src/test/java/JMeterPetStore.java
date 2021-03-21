import com.google.common.io.Resources;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;


public class JMeterPetStore {
    @Test
    public static void testPetstore() throws Exception {

        File jmeterProperties = new File("performance/jmeter.properties");
        if (jmeterProperties.exists()) {
            //JMeter Engine
            StandardJMeterEngine jmeter = new StandardJMeterEngine();

            //JMeter initialization (properties, log levels, locale, etc)
            JMeterUtils.setJMeterHome("performance/apache-jmeter");
            JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
            JMeterUtils.initLocale();

            // JMeter Test Plan, basically JOrphan HashTree
            HashTree testPlanTree = new HashTree();

            URL file = Resources.getResource("pet.json");
            String myJson = Resources.toString(file, Charset.defaultCharset());
            JSONObject json = new JSONObject( myJson );
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long id = timestamp.getTime();
            json.put("id", id);
            json.getJSONObject("category").put("id", id);

            HeaderManager manager = new HeaderManager();
            manager.add(new Header("accept", "application/json"));
            manager.add(new Header("Content-Type", "application/json"));
            manager.setName(JMeterUtils.getResString("header_manager_title")); // $NON-NLS-1$
            manager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
            manager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());

            // First HTTP Sampler - create pet with id 224411886633
            HTTPSamplerProxy createPet = new HTTPSamplerProxy();
            createPet.setProtocol("https");
            createPet.setDomain("petstore.swagger.io");
            createPet.setPath("v2/pet/");
            createPet.setMethod("POST");
            createPet.addNonEncodedArgument("", json.toString(), "");
            createPet.setPostBodyRaw(true);
            createPet.setName("Create Pet");
            createPet.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
            createPet.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

            // Second request get pet details
            HTTPSamplerProxy getPetDetails = new HTTPSamplerProxy();
            getPetDetails.setProtocol("https");
            getPetDetails.setDomain("petstore.swagger.io");
            getPetDetails.setPath("v2/pet/" + id);
            getPetDetails.setMethod("GET");
            getPetDetails.setName("Get Pet Details");
            getPetDetails.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
            getPetDetails.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

            // Third request delete pet
            HTTPSamplerProxy deletePet = new HTTPSamplerProxy();
            deletePet.setProtocol("https");
            deletePet.setDomain("petstore.swagger.io");
            deletePet.setPath("v2/pet/" + id);
            deletePet.setMethod("DELETE");
            deletePet.setName("Delete Pet");
            deletePet.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
            deletePet.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());


            // Loop Controller
            LoopController loopController = new LoopController();
            loopController.setLoops(1);
            loopController.setFirst(true);
            loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
            loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
            loopController.initialize();

            // Thread Group
            ThreadGroup threadGroup = new ThreadGroup();
            threadGroup.setName("Example Thread Group");
            threadGroup.setNumThreads(1);
            threadGroup.setRampUp(1);
            threadGroup.setSamplerController(loopController);
            threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
            threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

            // Test Plan
            TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
            testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
            testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());

            // Construct Test Plan from previously initialized elements
            testPlanTree.add(testPlan);
            HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
            threadGroupHashTree.add(manager);
            threadGroupHashTree.add(createPet);
            threadGroupHashTree.add(getPetDetails);
            threadGroupHashTree.add(deletePet);

            // save generated test plan to JMeter's .jmx file format
            SaveService.saveTree(testPlanTree, new FileOutputStream( "performance/example.jmx"));

            //add Summarizer output to get test progress in stdout like:
            // summary =      2 in   1.3s =    1.5/s Avg:   631 Min:   290 Max:   973 Err:     0 (0.00%)
            Summariser summer = null;
            String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
            if (summariserName.length() > 0) {
                summer = new Summariser(summariserName);
            }


            // Store execution results into a .jtl file
            String logFile = "performance/example.jtl";
            ResultCollector logger = new ResultCollector(summer);
            logger.setFilename(logFile);
            testPlanTree.add(testPlanTree.getArray()[0], logger);

            // Run Test Plan
            jmeter.configure(testPlanTree);
            jmeter.run();

            System.out.println("Test completed. See " + "performance/example.jtl file for results");
            System.out.println("JMeter .jmx script is available at " + "performance/example.jmx");
        }
        else
            System.err.println("jmeter.home property is not set or pointing to incorrect location");

    }
}