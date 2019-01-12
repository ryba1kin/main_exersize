import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = "com/bell/steps",
        tags = "@yandex",
        dryRun = false,
        strict = false,
        snippets = SnippetType.CAMELCASE
)
public class CucumberTest {
}
