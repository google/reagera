maven_jar(
  name = "hamcrest_maven",
  artifact = "org.hamcrest:hamcrest-core:1.3",
)

bind(
  name = "hamcrest",
  actual = "@hamcrest_maven//jar"
)

maven_jar(
  name = "junit_maven",
  artifact = "junit:junit:4.12"
)

bind(
  name = "junit",
  actual = "@junit_maven//jar"
)

maven_jar(
  name = "guava_maven",
  artifact = "com.google.guava:guava:19.0"
)

bind(
  name = "guava",
  actual = "@guava_maven//jar"
)

maven_jar(
  name = "truth_maven",
  artifact = "com.google.truth:truth:jar:0.28"
)

bind(
  name = "truth",
  actual = "@truth_maven//jar"
)
