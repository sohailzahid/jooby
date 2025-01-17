/*
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
import io.jooby.Server;
import io.jooby.netty.Netty;

module io.jooby.netty {
  exports io.jooby.netty;
  exports io.jooby.internal.netty;

  requires io.jooby;
  requires com.github.spotbugs.annotations;
  requires typesafe.config;
  requires org.slf4j;

  requires io.netty.transport;
  requires io.netty.codec.http;
  requires io.netty.handler;
  requires io.netty.common;
  requires io.netty.buffer;
  requires io.netty.codec;
  requires static io.netty.transport.classes.epoll;
  requires static io.netty.transport.classes.kqueue;

  provides Server with Netty;
}
