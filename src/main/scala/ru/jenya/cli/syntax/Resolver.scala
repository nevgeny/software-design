package ru.jenya.cli.syntax

import ru.jenya.cli.show.CLIShow
import ru.jenya.cli.syntax.data._

object Resolver {

  def resolve(l: List[Token],
              env: collection.Map[String, String],
              acc: List[String] = List())(implicit show: CLIShow[Token]): List[String] = l match {
    case Op("$") :: Str(key) :: xs => resolve(xs, env, env.getOrElse(key, "") :: acc)
    case Op("$") :: OpBr :: Str(key) :: ClBr :: xs => resolve(xs, env, env.getOrElse(key, "") :: acc)
    case SubstStr(s) :: xs =>
      val str = resolveString(s.toList, env).mkString
      resolve(xs, env, s""""$str"""" :: acc)
    case Nil => acc.reverse
    case x :: xs => resolve(xs, env, show.show(x) :: acc)
  }

  def resolveString(l: List[Char], env: collection.Map[String, String]): List[Char] = l match {
    case Nil => Nil
    case '$' :: '(' :: xs =>
      val key = xs.takeWhile(_ != ')').mkString
      val (_ :: tail) = xs.dropWhile(_ != ')')
      env.getOrElse(key, "").toList ++ resolveString(tail, env)
    case '$' :: xs =>
      val key = xs.takeWhile(c => Character.isAlphabetic(c) || Character.isDigit(c)).mkString
      val (_ :: tail) = xs.dropWhile(c => Character.isAlphabetic(c) || Character.isDigit(c))
      env.getOrElse(key, "").toList ++ resolveString(tail, env)
    case x :: xs => x :: resolveString(xs, env)
  }

  //
  //  def resolve(l: List[Token]): List[Token] = l match {
  //    case Str(s1)::Str(s2)::xs =>
  //    Str(s1++s2)::resolve(xs)
  //    case Nil => Nil
  //    case x::xs => x::resolve(xs)
  //  }
  //
  //  def resolve(l: List[Token], env: Map[String, String]): List[Token] = l match {
  //
  //    case Op("$")::OpBr::Str(key)::ClBr::xs =>
  //      Str(env.getOrElse(key, ""))::resolve(xs, env)
  //    case Op("$")::Str(key)::xs =>
  //      Str(env.getOrElse(key, ""))::resolve(xs, env)
  //    case x::xs => x::resolve(xs, env)
  //    case Nil => Nil
  //  }
  //

}
