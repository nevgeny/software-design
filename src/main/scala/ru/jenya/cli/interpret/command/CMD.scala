package ru.jenya.cli.interpret.command

import java.io.{InputStream, OutputStream}

trait CMD {
  def execute(s: String,
              args: List[String],
              in: InputStream,
              out: OutputStream,
              ctx: collection.mutable.Map[String, String]): Unit
}

object CMD {
  def resolve(s: String): CMD = s match {
    case "cat" => CatCMD
    case "echo" => EchoCMD
    case "pwd" => PWDCMD
    case "wc" => WCCMD
    case "=" => AssignCMD
    case _ => AnotherCMD
  }
}
