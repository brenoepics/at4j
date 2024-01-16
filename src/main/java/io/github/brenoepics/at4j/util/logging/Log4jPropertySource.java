package io.github.brenoepics.at4j.util.logging;

import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.PropertySource;

/**
 * This class implements the PropertySource interface from the Log4j utility package. It is used to
 * provide custom properties for the Log4j configuration.
 */
public class Log4jPropertySource implements PropertySource {

  /**
   * This method returns the priority of this PropertySource. The returned value is the minimum
   * integer value, indicating that any other PropertySource should override this one.
   *
   * @return the priority of this PropertySource
   */
  @Override
  public int getPriority() {
    // any value coming from somewhere else should beat us, we only want to change the default
    return Integer.MIN_VALUE;
  }

  /**
   * This method accepts a BiConsumer action and applies it to the property
   * "log4j.isThreadContextMapInheritable" with the value "true".
   *
   * @param action the BiConsumer action to be applied to the property
   */
  @Override
  public void forEach(BiConsumer<String, String> action) {
    action.accept("log4j.isThreadContextMapInheritable", "true");
  }

  /**
   * This method takes an Iterable of CharSequences as tokens and returns a CharSequence in the form
   * of "log4j." followed by the tokens joined as camel case.
   *
   * @param tokens an Iterable of CharSequences to be joined as camel case
   * @return a CharSequence in the form of "log4j." followed by the tokens joined as camel case
   */
  @Override
  public CharSequence getNormalForm(Iterable<? extends CharSequence> tokens) {
    return "log4j." + Util.joinAsCamelCase(tokens);
  }
}
