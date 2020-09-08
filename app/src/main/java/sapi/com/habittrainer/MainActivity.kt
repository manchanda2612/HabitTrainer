package sapi.com.habittrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import sapi.com.habittrainer.database.HabitDbTable

class MainActivity : AppCompatActivity() {

   //private var txvDescription : TextView? = null

    //----------------------------------------------------------------------------------------------


    /**
     * Now let's improve this one more step by using lateinit modifier.
     * So this means that this variable here doesn't need to be initialized at this point in time.
     * So this means we can not get rid of the nullability here.
     * and instead we can still use our code as before. We just have to make sure to actually
     * set a value to our member variable before using it
     * lateinit modifier allowed only on mutable properties
     */
    //private lateinit var txvDescription : TextView



    //----------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * If you haven't initialized the variable with null and try to access it like txvDescription.text then it will give you
         * compiler time error and told you either access the value by safe operator (?.) or non-safe operator (!!.)
         * If you access the variable without initializing it using safe operator(?.) then it will not throw Null Pointer Exception.
         * But if you access the variable without initializing it using non-safe operator(!!.) then it will throw NullPointer Exception because
         * in non-safe operator kotlin understand that you will handle null.
         */


         //  txvDescription!!.text = "A Refreshing glass of water gets you hyderated"

        /**
         * if txvDescription is null then the remaining part of it will not be executed and this all handle by kotlin itself.
         */
        //txvDescription?.text = "A Refreshing glass of water gets you hyderated"

        /**
         * In Java you normally have to cast findViewById but in kotlin you actually don't need it
         */
        //txvDescription = findViewById(R.id.txv_act_main_description)  // txvDescription = findViewById<TextView>(R.id.txv_act_main_description)
        //txvDescription?.text = "A Refreshing glass of water gets you hyderated"


    //----------------------------------------------------------------------------------------------

        //txvDescription = findViewById(R.id.txv_act_main_description)
        //txvDescription.text  = "A Refreshing glass of water gets you hyderated"


    //----------------------------------------------------------------------------------------------
        /**
         *  Kotlin extensions that gonna automatically go ahead and create member variables that we can
         * simply access in our code
         *  we can access the member variable without declaring it before,
         *  by simply using the name of the view in xml.
         *  This is most important point because this way we actually gonna reduce boilerplate code.
         */
        //img_act_main.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.walk))
       // txv_act_main_title.text = getString(R.string.water)
       // txv_act_main_description.text = getString(R.string.water_description)


    //----------------------------------------------------------------------------------------------

        rcl_act_main.setHasFixedSize(true)
        rcl_act_main.layoutManager = LinearLayoutManager(this)

    }

    override fun onResume() {
        super.onResume()
        rcl_act_main.adapter = HabitAdapter(HabitDbTable(this).getAllHabits())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.habit_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_habit) {

            /**
             * .class actually refers to kotlin class
             * so what we wanna do here, we wanna access the Java class
             * the way you do this, we should do a double colon
             */
            //val intent = Intent(this, CreateHabitActivity::class.java)
           // startActivity(intent)

            /**
             * we can make this process generic (kind of a utility method)
             * just pass the class name and that utility method will open that class
             */
            switchTo(CreateHabitActivity::class.java)



        }
        return true
    }

    private fun switchTo(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

}
