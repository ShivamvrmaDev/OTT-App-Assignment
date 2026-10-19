package com.shivam.ottvideoplayer.data.data_resource

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.shivam.ottvideoplayer.data.model.OTTDataModelClass

class LocalDataSource(
    private val context: Context
) : ContentDataSource {

    override suspend fun getHomeData(): OTTDataModelClass? {
        return try {
            context.assets.open("gluedin_final_home_content.json")
                .bufferedReader()
                .use { reader ->
                    Gson().fromJson(reader, OTTDataModelClass::class.java)
                }
        } catch (e: Exception) {
            Log.e("LocalDataSource", "Error reading local JSON asset data", e)
            null
        }
    }

    companion object {
        fun getDataFromAssets(context: Context): OTTDataModelClass? {
            return try {
                context.assets.open("gluedin_final_home_content.json")
                    .bufferedReader()
                    .use { reader ->
                        Gson().fromJson(reader, OTTDataModelClass::class.java)
                    }
            } catch (e: Exception) {
                Log.e("LocalDataSource", "Error reading asset in static method", e)
                null
            }
        }
    }
}
