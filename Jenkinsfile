pipeline {
    agent {
        docker{
            image 'android-29'
        }
    }
    stages {
        stage('Pull') {
            steps {
                checkout([
                    $class: 'GitSCM', 
                    branches: [[name: '*/testing']], 
                    extensions: [[$class: 'CleanCheckout']], 
                    submoduleCfg: [], 
                    userRemoteConfigs: [[url: 'https://github.com/bonusdev/BattleOfMindsGame']]
                ])
            }
        }
        stage('Build') {
            steps {
                sh 'gradle startPipeline'
            }
        }
    }
    
}
