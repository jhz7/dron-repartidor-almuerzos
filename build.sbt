name := "dron-repartidor-almuerzos"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.typelevel"   %%  "cats-core"    % "1.4.0"  withSources()
  ,"org.typelevel"  %%  "cats-kernel"  % "1.4.0"  withSources()
  ,"org.typelevel"  %%  "cats-macros"  % "1.4.0"  withSources()
  ,"io.monix"       %%  "monix"        % "3.0.0-RC1"
  ,"org.scalatest"  %%  "scalatest"    % "3.0.5"  % "test"
)