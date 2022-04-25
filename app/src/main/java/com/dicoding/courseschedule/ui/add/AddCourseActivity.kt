package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private var tagTimePicker : String? = null
    private var startTime = ""
    private var endTime = ""

    private var _binding : ActivityAddCourseBinding? = null
    private val binding get() = _binding !!

    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tagTimePicker = TAG_START
        addCourseViewModel = AddCourseViewModel(
            DataRepository.getInstance(applicationContext)!!
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getByDayName(dayName : String) : Int{
        return when(dayName){
            "Monday" -> 1
            "Tuesday" -> 2
            "Wednesday" -> 3
            "Thursday" -> 4
            "Friday" -> 5
            "Saturday" -> 6
            "Sunday" -> 7
            else -> 1
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_insert -> {
                val day = binding.spinnerDay.selectedItem.toString()
                val dayNumber = getByDayName(dayName = day)
                addCourseViewModel.insertCourse(
                    binding.addCourseName.text.toString(),
                    dayNumber, startTime, endTime,
                    binding.addLecturerName.text.toString(),
                    binding.addNote.text.toString()
                )
                finish()
                addCourseViewModel.saved.observe(this,{
                    event -> event.getContentIfNotHandled().let{
                        isSaved -> if (isSaved == true)
                        {
                            val text = "New Schedule Has Been Saved"
                            val duration = Toast.LENGTH_SHORT
                            Toast.makeText(applicationContext, text, duration).show()
                        }
                    }
                })
            }
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showTimePicker(view : View){
        val dialogFragment = TimePickerFragment()
        when(view.id){
            R.id.add_img_start_time -> {
                tagTimePicker = TAG_START
                dialogFragment.show(supportFragmentManager, tagTimePicker)
            }
            R.id.add_img_end_time -> {
                tagTimePicker = TAG_END
                dialogFragment.show(supportFragmentManager, tagTimePicker)
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        if (tagTimePicker == TAG_START){
            startTime = String.format("%02d", hour) + ":" + String.format("%02d", minute)
            binding.startTime.text = startTime
        }
        else if (tagTimePicker == TAG_END){
            endTime = String.format("%02d", hour) + ":" + String.format("%02d", minute)
            binding.endTime.text = endTime
        }
    }

    companion object{
        const val TAG_START = "timePickerStart"
        const val TAG_END = "timePickerEnd"
    }
}