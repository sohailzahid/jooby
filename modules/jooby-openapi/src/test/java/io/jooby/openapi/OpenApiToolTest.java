package io.jooby.openapi;

import com.fasterxml.jackson.databind.JavaType;
import examples.Letter;
import examples.MvcApp;
import examples.RouteFormArgs;
import examples.RouteImport;
import examples.RouteImportReferences;
import examples.RoutePathArgs;
import examples.RouteQueryArgs;
import examples.RouteIdioms;
import examples.RouteInline;
import examples.RoutePatternIdioms;
import examples.RouteReturnTypeApp;
import examples.ABean;
import examples.RouterProduceConsume;
import io.jooby.internal.openapi.DebugOption;
import kt.KtCoroutineRouteIdioms;
import kt.KtMvcApp;
import kt.KtRouteIdioms;
import kt.KtRouteImport;
import kt.KtRouteRef;
import kt.KtRouteReturnType;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenApiToolTest {
  @OpenApiTest(value = RoutePatternIdioms.class)
  public void routePatternIdioms(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /variable", route.toString());
        })
        .next(route -> {
          assertEquals("DELETE /variable/{id}", route.toString());
        })
        .next(route -> {
          assertEquals("POST /variable/foo", route.toString());
        })
        .next(route -> {
          assertEquals("PUT /variable/variable/foo", route.toString());
        })
        .verify();
  }

  @OpenApiTest(value = RouteInline.class)
  public void routeInline(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /inline", route.toString());
        })
        .verify();
  }

  @OpenApiTest(value = RouteIdioms.class)
  public void routeIdioms(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /aaa/bbb", route.toString());
        })
        .next(route -> {
          assertEquals("GET /aaa/ccc/ddd", route.toString());
        })
        .next(route -> {
          assertEquals("GET /aaa/eee", route.toString());
        })
        .next(route -> {
          assertEquals("GET /inline", route.toString());
        })
        .next(route -> {
          assertEquals("GET /routeReference", route.toString());
        })
        .next(route -> {
          assertEquals("GET /staticRouteReference", route.toString());
        })
        .next(route -> {
          assertEquals("GET /externalReference", route.toString());
        })
        .next(route -> {
          assertEquals("GET /externalStaticReference", route.toString());
        })
        .next(route -> {
          assertEquals("GET /alonevar", route.toString());
        })
        .next(route -> {
          assertEquals("GET /aloneinline", route.toString());
        })
        .next(route -> {
          assertEquals("GET /lambdaRef", route.toString());
        })
        .verify();
  }

  @OpenApiTest(value = KtRouteIdioms.class)
  public void ktRoute(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /implicitContext", route.toString());
        })
        .next(route -> {
          assertEquals("GET /explicitContext", route.toString());
        })
        .next(route -> {
          assertEquals("GET /api/people", route.toString());
        })
        .next(route -> {
          assertEquals("GET /api/version", route.toString());
        })
        .verify();
  }

  @OpenApiTest(value = KtCoroutineRouteIdioms.class)
  public void ktCoroutineRoute(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /version", route.toString());
        })
        .next(route -> {
          assertEquals("PATCH /api/version", route.toString());
        })
        .next(route -> {
          assertEquals("GET /api/people", route.toString());
        })
        .verify();
  }

  @OpenApiTest(value = RouteQueryArgs.class)
  public void routeQueryArguments(RouteIterator iterator) {
    iterator
        .next((route, args) -> {
          assertEquals("GET /", route.toString());

          args
              .next(it -> {
                assertEquals(String.class.getName(), it.getJavaType());
                assertEquals("str", it.getName());
                assertNull(it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals(int.class.getName(), it.getJavaType());
                assertEquals("i", it.getName());
                assertNull(it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.List<java.lang.String>", it.getJavaType());
                assertEquals("listStr", it.getName());
                assertNull(it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.List<java.lang.Double>", it.getJavaType());
                assertEquals("listType", it.getName());
                assertNull(it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals(String.class.getName(), it.getJavaType());
                assertEquals("defstr", it.getName());
                assertEquals("default", it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals(int.class.getName(), it.getJavaType());
                assertEquals("defint", it.getName());
                assertEquals(87, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals(int.class.getName(), it.getJavaType());
                assertEquals("defint0", it.getName());
                assertEquals(0, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals(boolean.class.getName(), it.getJavaType());
                assertEquals("defbool", it.getName());
                assertEquals(true, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Optional<java.lang.String>", it.getJavaType());
                assertEquals("optstr", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Optional<java.lang.Integer>", it.getJavaType());
                assertEquals("optint", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Optional<java.lang.String>", it.getJavaType());
                assertEquals("optstr2", it.getName());
                assertEquals("optional", it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals("java.lang.Integer", it.getJavaType());
                assertEquals("toI", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertNull(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals(Letter.class.getName(), it.getJavaType());
                assertEquals("letter", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Map<java.lang.String,java.lang.String>", it.getJavaType());
                assertEquals("query", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertFalse(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Map<java.lang.String,java.util.List<java.lang.String>>",
                    it.getJavaType());
                assertEquals("queryList", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertFalse(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .next(it -> {
                assertEquals(ABean.class.getName(), it.getJavaType());
                assertEquals("query", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertNull(it.getRequired());
                assertFalse(it.isSingle());
                assertEquals("query", it.getIn());
              })
              .verify();
        })
        .verify();
  }

  @OpenApiTest(value = RoutePathArgs.class)
  public void routePathArguments(RouteIterator iterator) {
    iterator
        .next((route, args) -> {
          assertEquals("GET /", route.toString());
          args
              .next(it -> {
                assertEquals(String.class.getName(), it.getJavaType());
                assertEquals("str", it.getName());
                assertNull(it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals(int.class.getName(), it.getJavaType());
                assertEquals("i", it.getName());
                assertNull(it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.List<java.lang.String>", it.getJavaType());
                assertEquals("listStr", it.getName());
                assertNull(it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.List<java.lang.Double>", it.getJavaType());
                assertEquals("listType", it.getName());
                assertNull(it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals(String.class.getName(), it.getJavaType());
                assertEquals("defstr", it.getName());
                assertEquals("default", it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals(int.class.getName(), it.getJavaType());
                assertEquals("defint", it.getName());
                assertEquals(87, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals(int.class.getName(), it.getJavaType());
                assertEquals("defint0", it.getName());
                assertEquals(0, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals(boolean.class.getName(), it.getJavaType());
                assertEquals("defbool", it.getName());
                assertEquals(true, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Optional<java.lang.String>", it.getJavaType());
                assertEquals("optstr", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Optional<java.lang.Integer>", it.getJavaType());
                assertEquals("optint", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Optional<java.lang.String>", it.getJavaType());
                assertEquals("optstr2", it.getName());
                assertEquals("optional", it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals("java.lang.Integer", it.getJavaType());
                assertEquals("toI", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals(Letter.class.getName(), it.getJavaType());
                assertEquals("letter", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Map<java.lang.String,java.lang.String>", it.getJavaType());
                assertEquals("path", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertFalse(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Map<java.lang.String,java.util.List<java.lang.String>>",
                    it.getJavaType());
                assertEquals("pathList", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertFalse(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .next(it -> {
                assertEquals(ABean.class.getName(), it.getJavaType());
                assertEquals("path", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertFalse(it.isSingle());
                assertEquals("path", it.getIn());
              })
              .verify();
        })
        .verify();
  }

  @OpenApiTest(value = RouteFormArgs.class)
  public void routeFormArguments(RouteIterator iterator) {
    iterator
        .next((route, args) -> {
          assertEquals("GET /", route.toString());

          args
              .next(it -> {
                assertEquals(String.class.getName(), it.getJavaType());
                assertEquals("str", it.getName());
                assertNull(it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals(int.class.getName(), it.getJavaType());
                assertEquals("i", it.getName());
                assertNull(it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.List<java.lang.String>", it.getJavaType());
                assertEquals("listStr", it.getName());
                assertNull(it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.List<java.lang.Double>", it.getJavaType());
                assertEquals("listType", it.getName());
                assertNull(it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals(String.class.getName(), it.getJavaType());
                assertEquals("defstr", it.getName());
                assertEquals("default", it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals(int.class.getName(), it.getJavaType());
                assertEquals("defint", it.getName());
                assertEquals(87, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals(int.class.getName(), it.getJavaType());
                assertEquals("defint0", it.getName());
                assertEquals(0, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals(boolean.class.getName(), it.getJavaType());
                assertEquals("defbool", it.getName());
                assertEquals(true, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Optional<java.lang.String>", it.getJavaType());
                assertEquals("optstr", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Optional<java.lang.Integer>", it.getJavaType());
                assertEquals("optint", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Optional<java.lang.String>", it.getJavaType());
                assertEquals("optstr2", it.getName());
                assertEquals("optional", it.getDefaultValue());
                assertFalse(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals("java.lang.Integer", it.getJavaType());
                assertEquals("toI", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertNull(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals(Letter.class.getName(), it.getJavaType());
                assertEquals("letter", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertTrue(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Map<java.lang.String,java.lang.String>", it.getJavaType());
                assertEquals("form", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertFalse(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals("java.util.Map<java.lang.String,java.util.List<java.lang.String>>",
                    it.getJavaType());
                assertEquals("formList", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertTrue(it.getRequired());
                assertFalse(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .next(it -> {
                assertEquals(ABean.class.getName(), it.getJavaType());
                assertEquals("form", it.getName());
                assertEquals(null, it.getDefaultValue());
                assertNull(it.getRequired());
                assertFalse(it.isSingle());
                assertEquals("form", it.getIn());
              })
              .verify();
        })
        .verify();
  }

  public static class Java {
    private JavaType type;

    public JavaType getType() {
      return type;
    }

    public void setType(JavaType type) {
      this.type = type;
    }

    @Override public String toString() {
      return type.toString();
    }
  }

  @OpenApiTest(value = RouteReturnTypeApp.class, ignoreArguments = true)
  public void routeReturnType(RouteIterator iterator) {
    //    ObjectMapper mapper = new ObjectMapper();
    //    Java java = new Java();
    //    java.type = mapper.constructType(int[].class);
    //    String json = mapper.writeValueAsString(java);
    //    System.out.println(json);
    //    Java j = mapper.readValue(json, Java.class);
    //    System.out.println(j);
    iterator
        .next(route -> {
          assertEquals("GET /literal/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /literal/2", route.toString());
          assertEquals(Integer.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /literal/3", route.toString());
          assertEquals(Object.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /literal/4", route.toString());
          assertEquals(Boolean.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/1", route.toString());
          assertEquals(RouteReturnTypeApp.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/2", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/3", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/4", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/5", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/6", route.toString());
          assertEquals("java.util.List<java.lang.String>", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /generic/1", route.toString());
          assertEquals(Object.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /generic/2", route.toString());
          assertEquals(Integer.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /generic/3", route.toString());
          assertEquals(Object.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /generic/4", route.toString());
          assertEquals(Callable.class.getName() + "<" + Byte.class.getName() + ">",
              route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /generic/5", route.toString());
          assertEquals(Callable.class.getName() + "<" + Character.class.getName() + ">",
              route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /localvar/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /localvar/2", route.toString());
          assertEquals(Integer.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /localvar/3", route.toString());
          assertEquals("[Ljava.lang.String;", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /localvar/4", route.toString());
          assertEquals("[F", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /complexType/1", route.toString());
          assertEquals("java.util.List", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /complexType/2", route.toString());
          assertEquals("java.util.List<java.lang.String>", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /complexType/3", route.toString());
          assertEquals("java.util.List<java.util.List<java.lang.String>>",
              route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /multipleTypes/1", route.toString());
          assertEquals("java.util.ArrayList", route.getResponse().getJavaTypes().get(0));
          assertEquals("java.util.LinkedList", route.getResponse().getJavaTypes().get(1));
        })
        .next(route -> {
          assertEquals("GET /multipleTypes/2", route.toString());
          assertEquals("examples.ABean", route.getResponse().getJavaTypes().get(0));
          assertEquals("examples.BBean", route.getResponse().getJavaTypes().get(1));
        })
        .next(route -> {
          assertEquals("GET /multipleTypes/3", route.toString());
          assertEquals("examples.Bean", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /array/1", route.toString());
          assertEquals("[Z", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /array/2", route.toString());
          assertEquals("[Lexamples.RouteReturnTypeApp;", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /array/3", route.toString());
          assertEquals("[I", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /array/4", route.toString());
          assertEquals("[Ljava.lang.String;", route.getResponse().getJavaType());
        })
        .verify();
  }

  @OpenApiTest(value = RouteImport.class)
  public void routeImport(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /main/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /main/submain/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /require/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /subroute/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .verify();
  }

  @OpenApiTest(value = RouteImportReferences.class)
  public void routeImportReferences(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /require/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /prefix/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .verify();
  }

  @OpenApiTest(value = KtRouteImport.class)
  public void ktRouteImport(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /main/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /main/submain/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /require/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /subroute/a/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .verify();
  }

  @OpenApiTest(value = KtRouteReturnType.class, ignoreArguments = true)
  public void ktRouteReturnType(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /literal/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /literal/2", route.toString());
          assertEquals(Integer.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /literal/3", route.toString());
          assertEquals(void.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /literal/4", route.toString());
          assertEquals(Boolean.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/1", route.toString());
          assertEquals(KtRouteReturnType.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/2", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/3", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/4", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/5", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /call/6", route.toString());
          assertEquals("java.util.List<java.lang.String>", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /generic/1", route.toString());
          assertEquals(Object.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /generic/2", route.toString());
          assertEquals(Integer.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /generic/3", route.toString());
          assertEquals(Object.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /generic/4", route.toString());
          assertEquals(Callable.class.getName() + "<" + Byte.class.getName() + ">",
              route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /localvar/1", route.toString());
          assertEquals(String.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /localvar/2", route.toString());
          assertEquals(Integer.class.getName(), route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /localvar/3", route.toString());
          assertEquals("[Ljava.lang.String;", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /localvar/4", route.toString());
          assertEquals("[F", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /complexType/1", route.toString());
          assertEquals("java.util.List", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /complexType/2", route.toString());
          assertEquals("java.util.List<java.lang.String>", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /complexType/3", route.toString());
          assertEquals("java.util.List<java.util.List<java.lang.String>>",
              route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /multipleTypes/1", route.toString());
          assertEquals("java.util.ArrayList", route.getResponse().getJavaTypes().get(0));
          assertEquals("java.util.LinkedList", route.getResponse().getJavaTypes().get(1));
        })
        .next(route -> {
          assertEquals("GET /multipleTypes/2", route.toString());
          assertEquals("examples.ABean", route.getResponse().getJavaTypes().get(0));
          assertEquals("examples.BBean", route.getResponse().getJavaTypes().get(1));
        })
        .next(route -> {
          assertEquals("GET /multipleTypes/3", route.toString());
          assertEquals("examples.Bean", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /array/1", route.toString());
          assertEquals("[Z", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /array/2", route.toString());
          assertEquals("java.util.List<kt.KtRouteReturnType>", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /array/3", route.toString());
          assertEquals("[I", route.getResponse().getJavaType());
        })
        .next(route -> {
          assertEquals("GET /array/4", route.toString());
          assertEquals("[Ljava.lang.String;", route.getResponse().getJavaType());
        })
        .verify();
  }

  @OpenApiTest(value = RouterProduceConsume.class)
  public void routeProduceConsume(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /", route.toString());
          assertEquals(Arrays.asList("text/html", "text/plain", "some/type"), route.getProduces());
          assertEquals(Arrays.asList("application/json", "application/javascript"),
              route.getConsumes());
        })
        .next(route -> {
          assertEquals("GET /json", route.toString());
          assertEquals(Arrays.asList("application/json"), route.getProduces());
          assertEquals(Arrays.asList("application/json"), route.getConsumes());
        })
        .next(route -> {
          assertEquals("GET /api/people", route.toString());
          assertEquals(Arrays.asList("application/yaml"), route.getProduces());
          assertEquals(Arrays.asList("application/yaml"), route.getConsumes());
        })
        .verify();
  }

  @OpenApiTest(value = MvcApp.class)
  public void routeMvc(RouteIterator iterator) {
    iterator
        .next((route, args) -> {
          assertEquals("GET /api/foo", route.toString());
          args
              .next(arg -> {
                assertEquals("q", arg.getName());
                assertEquals("java.util.Optional<java.lang.String>", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertFalse(arg.getRequired());
              })
              .verify();
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next((route, args) -> {
          assertEquals("GET /api/bar", route.toString());
          args
              .next(arg -> {
                assertEquals("q", arg.getName());
                assertEquals("java.util.Optional<java.lang.String>", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertFalse(arg.getRequired());
              })
              .verify();
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next((route, args) -> {
          assertEquals("GET /api", route.toString());

          args
              .next(arg -> {
                assertEquals("bool", arg.getName());
                assertEquals("boolean", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("s", arg.getName());
                assertEquals("short", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("i", arg.getName());
                assertEquals("int", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("c", arg.getName());
                assertEquals("char", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("l", arg.getName());
                assertEquals("long", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("f", arg.getName());
                assertEquals("float", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("d", arg.getName());
                assertEquals("double", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .verify();
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next((route, args) -> {
          assertEquals("POST /api/post", route.toString());
          args
              .next(arg -> {
                assertEquals("bool", arg.getName());
                assertEquals("boolean", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("s", arg.getName());
                assertEquals("short", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("i", arg.getName());
                assertEquals("int", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("c", arg.getName());
                assertEquals("char", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("l", arg.getName());
                assertEquals("long", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("f", arg.getName());
                assertEquals("float", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("d", arg.getName());
                assertEquals("double", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .verify();
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next((route, args) -> {
          assertEquals("GET /api/path", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /api/path-only", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /api/session", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
          assertFalse(route.getDeprecated());
        })
        .next(route -> {
          assertEquals("GET /api/returnList", route.toString());
          assertEquals("java.util.List<" + String.class.getName() + ">",
              route.getResponse().toString());
          assertTrue(route.getDeprecated());
        })
        .verify();
  }

  @OpenApiTest(value = KtMvcApp.class)
  public void ktMvc(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("GET /", route.toString());
          assertEquals(String.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("DELETE /unit", route.toString());
          assertEquals(void.class.getName(), route.getResponse().toString());
        })
        .next(route -> {
          assertEquals("GET /doMap", route.toString());
          assertEquals("java.util.Map<java.lang.String,java.lang.Object>",
              route.getResponse().toString());
        })
        .next((route, args) -> {
          assertEquals("GET /doParams", route.toString());
          assertEquals(ABean.class.getName(), route.getResponse().toString());
          args
              .next(arg -> {
                assertEquals("I", arg.getName());
                assertEquals("int", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("oI", arg.getName());
                assertEquals("java.lang.Integer", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertFalse(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("q", arg.getName());
                assertEquals("java.lang.String", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("nullq", arg.getName());
                assertEquals("java.lang.String", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertFalse(arg.getRequired());
              })
              .verify();
        })
        .next((route, args) -> {
          assertEquals("GET /coroutine", route.toString());
          assertEquals("java.util.List<" + String.class.getName() + ">",
              route.getResponse().toString());
        })
        .next((route, args) -> {
          assertEquals("GET /future", route.toString());
          assertEquals("java.lang.String", route.getResponse().toString());
        })
        .next((route, args) -> {
          assertEquals("GET /httpNames", route.toString());
          assertEquals("java.lang.String", route.getResponse().toString());
          args
              .next(arg -> {
                assertEquals("Last-Modified-Since", arg.getName());
                assertEquals("java.lang.String", arg.getJavaType());
                assertEquals("header", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .next(arg -> {
                assertEquals("x-search", arg.getName());
                assertEquals("java.lang.String", arg.getJavaType());
                assertEquals("query", arg.getIn());
                assertTrue(arg.getRequired());
              })
              .verify();
        })
        .verify();
  }

  @OpenApiTest(value = KtRouteRef.class)
  public void ktRouteRef(RouteIterator iterator) {
    iterator
        .next(route -> {
          assertEquals("Create a Pet", route.getSummary());
          assertEquals("createPetRef", route.getOperationId());
        });
  }
}