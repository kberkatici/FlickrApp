package com.example.kotlinfirebaseflickr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfirebaseflickr.adapter.RecyclerViewAdapter
import com.example.kotlinfirebaseflickr.model.FlickrModel
import com.example.kotlinfirebaseflickr.service.FlickrAPI
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private lateinit var auth: FirebaseAuth
    private val BASE_URL = "https://www.flickr.com/"
    private var flickrModels :ArrayList<FlickrModel>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser !=null) {
            val intent = Intent(applicationContext,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager( this)
    recyclerView.layoutManager = layoutManager

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(FlickrAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<FlickrModel>> {
            override fun onResponse(
                call: Call<List<FlickrModel>>,
                response: Response<List<FlickrModel>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        flickrModels = ArrayList(it)
                        flickrModels?.let{

                        recyclerViewAdapter = RecyclerViewAdapter(flickrModels!!,this@MainActivity)
                        recyclerView.adapter = recyclerViewAdapter
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<FlickrModel>>, t: Throwable) {
                t.printStackTrace()
            }


        })

    }

    fun signInClicked(view:View){

        auth.signInWithEmailAndPassword(useremailtext.text.toString(),passwordText.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext,"Welcome: ${auth.currentUser?.email.toString()}",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
        }

    }
    fun signUpClicked(view:View){

        val email= useremailtext.text.toString()
        val password= passwordText.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            if (exception != null) {
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onItemClick(flickrModel: FlickrModel) {
    Toast.makeText(this,"Clicked: ${flickrModel.owner}",Toast.LENGTH_LONG).show()
    }
}

