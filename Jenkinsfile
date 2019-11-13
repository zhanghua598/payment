pipeline {
    agent any
    environment {
      
	    GIT_URL = "git@github.ibm.com:sba/payment.git"
		GIT_CRED = "48zhangh46d03-31f8-4cee-a4ed-c138e7bzhangh00a0"
		DOCKER_REPO="registry.cn-chengdu.aliyuncs.com/yuanbing/sba-payment"
		DOCKER_REG="https://registry.cn-chengdu.aliyuncs.com"
		DOCKER_REG_KEY = "874c3zhangh4zhangh-6135-41d1-zhangh02c-ebd1841zhangh3ded"
		dockerImage = ''
      
    }
    stages {
    
    	stage('CheckOut Code'){
         	steps{
            	checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: GIT_CRED, url: GIT_URL]]])
            	}
              }
        stage('Maven Build'){
        	steps{
        	    sh 'mvn clean install -DskipTests'
        	}

        }
        
        stage('Building image') {
	      steps{
	        script {
	           docker.withRegistry( DOCKER_REG, DOCKER_REG_KEY ) {dockerImage = docker.build DOCKER_REPO + ":$BUILD_NUMBER"
	           }
	        }
	      }
	    }
	    stage('Push Image') {
      steps{
        script {
		   docker.withRegistry( DOCKER_REG, DOCKER_REG_KEY ) {
		            dockerImage.push()
		          }
		        }
		      }
		}
		
		stage('Deploy Image to K8s') {
      steps{
        script {
        	sh "sed -i 's/{version}/" + BUILD_NUMBER + "/g' deployment.yaml"
	   		sh 'kubectl apply -f deployment.yaml'
		      }
		}
		}
		
		
		stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $DOCKER_REPO:$BUILD_NUMBER"
      }
    }
   }
  

}