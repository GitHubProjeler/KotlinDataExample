package com.trkaynak.kotlindataexample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var nameEditText: EditText? = null
    private var surnameEditText: EditText? = null
    private var ageEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.nameeditText) as EditText
        surnameEditText = findViewById(R.id.surnameeditText2) as EditText
        ageEditText = findViewById(R.id.ageeditText3) as EditText

        findViewById<Button>(R.id.savebutton).setOnClickListener { addArtist() }

        findViewById<Button>(R.id.showbutton2).setOnClickListener {
            val intent = Intent(applicationContext,ViewArtistsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addArtist() {
        val name = nameEditText?.text.toString()
        val surname = surnameEditText?.text.toString()
        var age = ageEditText?.text.toString()

        val stringRequest = object : StringRequest(Request.Method.POST,EndPoints.URL_ADD_ARTIST,
           Response.Listener <String>{ response ->
               try {
                   val obj = JSONObject(response)
                   Toast.makeText(applicationContext,obj.getString("message"),Toast.LENGTH_LONG).show()
               } catch (e: Exception){
                   e.printStackTrace()
               }

           },
            object : Response.ErrorListener{
                override fun onErrorResponse(volleyError: VolleyError?) {
                    Toast.makeText(applicationContext,volleyError?.message,Toast.LENGTH_LONG).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("name",name)
                params.put("surname",surname)
                params.put("age",age)
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}
