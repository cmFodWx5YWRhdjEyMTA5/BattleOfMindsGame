class LoadPropertiesPlugin : Plugin<Project> {


    override fun apply(project: Project) {

        project.task("loadFakeFiles") {
            loadApi(project)
            loadJson(project)
            doLast {
                println("Fake file created")
            }
        }


    }

    private fun loadApi(project: Project) {
        //api.properties
        val apiPropertiesFile = project.file("api.properties")
        if (!apiPropertiesFile.exists()) {
            val info =
                    "STORAGE_API_KEY=\"fake\"\n" +
                            "STORAGE_BUCKET=\"fake\"\n" +
                            "STORAGE_SERVICE=\"fake\"\n" +
                            "PREFIX_ALL=\"fake\"\n" +
                            "STORAGE_BASE_URL=\"fake\"\n" +
                            "PREFIX_MONSTER=\"fake\"\n"
            apiPropertiesFile.writeText(info)
        }
    }

    private fun loadJson(project: Project) {
        val googleServicesFile = project.file("google-services.json")
        val jsonPackageName = "com.bonusgaming.battleofmindskotlin.webimpl"

        if (!googleServicesFile.exists()) {
            val info = "{\n" +
                    "  \"project_info\": {\n" +
                    "    \"project_number\": \"fake\",\n" +
                    "    \"firebase_url\": \"fake\",\n" +
                    "    \"project_id\": \"fake\",\n" +
                    "    \"storage_bucket\": \"fake\"\n" +
                    "  },\n" +
                    "  \"client\": [\n" +
                    "    {\n" +
                    "      \"client_info\": {\n" +
                    "        \"mobilesdk_app_id\": \"fake\",\n" +
                    "        \"android_client_info\": {\n" +
                    "          \"package_name\": \"$jsonPackageName\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"oauth_client\": [\n" +
                    "        {\n" +
                    "          \"client_id\": \"fake\",\n" +
                    "          \"client_type\": 0,\n" +
                    "          \"android_info\": {\n" +
                    "            \"package_name\": \"$jsonPackageName\",\n" +
                    "            \"certificate_hash\": \"fake\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"client_id\": \"fake\",\n" +
                    "          \"client_type\": 0,\n" +
                    "          \"android_info\": {\n" +
                    "            \"package_name\": \"$jsonPackageName\",\n" +
                    "            \"certificate_hash\": \"fake\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"client_id\": \"fake\",\n" +
                    "          \"client_type\": 0,\n" +
                    "          \"android_info\": {\n" +
                    "            \"package_name\": \"$jsonPackageName\",\n" +
                    "            \"certificate_hash\": \"fake\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"client_id\": \"fake\",\n" +
                    "          \"client_type\": 0\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"api_key\": [\n" +
                    "        {\n" +
                    "          \"current_key\": \"fake\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"services\": {\n" +
                    "        \"appinvite_service\": {\n" +
                    "          \"other_platform_oauth_client\": [\n" +
                    "            {\n" +
                    "              \"client_id\": \"fake\",\n" +
                    "              \"client_type\": 0\n" +
                    "            }\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"configuration_version\": \"fake\"\n" +
                    "}"
            googleServicesFile.writeText(info)
        }
    }

}
apply<LoadPropertiesPlugin>()
