package xyz.idtlabs.icommit.fieldui

import org.mifos.mobile.cn.TestDataFactory

/**
 * FakeRemoteDataSource is reading the local json files into the java object using gson.
 * Created by Rajan Maurya on 25/6/17.
 */
class FakeRemoteDataSource {

    companion object {

        private val testDataFactory = TestDataFactory()

        /*fun getTestJson(): TestJson {
            return testDataFactory.convertJsonToDataObject(object : TypeToken<TestJson>() {
            }, FakeJsonName.TEST_JSON)
        }*/
    }
}
