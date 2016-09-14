package org.jooby.xss;

import org.jooby.Env;
import org.jooby.Jooby.Module;

import com.coverity.security.Escape;
import com.google.inject.Binder;
import com.typesafe.config.Config;

/**
 * <h1>xss</h1>
 * <p>
 * Lightweight set of escaping routines for fixing cross-site scripting (XSS) via
 * <a href="https://github.com/coverity/coverity-security-library">coverity-security-library</a>
 * </p>
 *
 * <h2>exports</h2>
 * <ul>
 * <li><strong>html</strong> escaper: HTML entity escaping for text content and attributes.</li>
 * <li><strong>htmlText</strong> escaper: Faster HTML entity escaping for tag content or quoted
 * attributes values only.</li>
 * <li><strong>js</strong> escaper: JavaScript String Unicode escaper.</li>
 * <li><strong>jsRegex</strong> escaper: JavaScript regex content escaper.</li>
 * <li><strong>css</strong> escaper: CSS String escaper.</li>
 * <li><strong>uri</strong> escaper: URI encoder.</li>
 * </ul>
 *
 * <h2>usage</h2>
 * <pre>{@code
 * {
 *   use(new XSS());
 *
 *   post("/", req -> {
 *     String safeHtml = req.param("text", "html").value();
 *   });
 * }
 * }</pre>
 *
 * <p>
 * Nested context are supported by providing multiple encoders:
 * </p>
 *
 * <pre>{@code
 * {
 *   use(new XSS());
 *
 *   post("/", req -> {
 *     String safeHtml = req.param("text", "js", "html", "uri").value();
 *   });
 * }
 * }</pre>
 *
 * <p>
 * Encoders run in the order they are provided.
 * </p>
 * <p>
 * If you want to learn more about nested context and why they are important have a look at this
 * <a href=
 * "http://security.coverity.com/document/2013/Mar/fixing-xss-a-practical-guide-for-developers.html">nice
 * guide</a> from
 * <a href="https://github.com/coverity/coverity-security-library">coverity-security-library</a>.
 * </p>
 *
 * @author edgar
 * @since 1.0.0
 */
public class XSS implements Module {

  @Override
  public void configure(final Env env, final Config conf, final Binder binder) {
    // contribute CSL escape functions
    env.xss("html", Escape::html)
        .xss("htmlText", Escape::htmlText)
        .xss("js", Escape::jsString)
        .xss("jsRegex", Escape::jsRegex)
        .xss("css", Escape::cssString)
        .xss("uri", Escape::uri);
  }

}
