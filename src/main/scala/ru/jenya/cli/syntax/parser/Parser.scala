package ru.jenya.cli.syntax.parser

import ru.jenya.cli.syntax.data.{Node, PipeLine, Token}

trait Parser {
  def parse(s: List[Token]): Either[String, PipeLine]
}