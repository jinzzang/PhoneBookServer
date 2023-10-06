node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    withSonarQubeEnv() {
      sh "./gradlew sonar -D"sonar.projectKey=demo" -D "sonar.host.url=http://192.168.56.1:9000" -D "sonar.token=sqp_c04b98ce2538f41082e52851e67a20d4a2bfe841""
    }
  }
}