package com.example.animalesjunin;

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var LOG_TAG = "kikopalomares"

    var SPREAD_SHEET_ID = "1frmdmKAesqtX8pVrT0mJNVnLkV1FqsHPAu9CdWVVHHM"
    var TABLE_USERS = "users"
    var TABLE_PETS = "pets"

    //IMPORTANTE: estas URL tienes que cambiarlas por las de tus propios scripts
    var sheetInJsonURL = "https://script.google.com/macros/s/AKfycbwPvEQym0tu7CwtnqiLBRPA9f5kCT7VM1HKkebgV-G9xDN977lkU_cSwhqCTD3bP1s/exec?spreadsheetId=$SPREAD_SHEET_ID"

    private lateinit var recyclerUserAdapter: RecyclerUserAdapter
    private lateinit var recyclerPetAdapter: RecyclerPetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUsers()
    }

    fun initList(){
        recyclerView.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL

        llm.reverseLayout = true
        llm.stackFromEnd = true

        recyclerView.layoutManager = llm

        progressBar.visibility = View.GONE
    }

    /**
     * Obtenemos los datos de la hoja de users
     */
    fun getUsers(){
        val queue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, sheetInJsonURL + TABLE_USERS, null,
                Response.Listener { response ->
                    Log.i(LOG_TAG, "Response is: $response")

                    val gson = Gson()
                    val jsonArray: JsonArray = gson.fromJson<JsonArray>(response.toString(), JsonArray::class.java)

                    for (element: JsonElement in jsonArray){
                        Log.i(LOG_TAG, element.asJsonObject.toString())
                    }

                    recyclerUserAdapter = RecyclerUserAdapter(jsonArray)
                    initList()
                    recyclerView.adapter = recyclerUserAdapter

                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Log.e(LOG_TAG, "That didn't work!")
                }
        )
        queue.add(jsonArrayRequest)
    }