/**
 * Created by Kurt on 4/9/17.
 */
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "IdeaProjects/CEN3031/Test/Features", plugin = {"pretty", "html:target/cucumber"})
public class CucumberRUnner {
}
