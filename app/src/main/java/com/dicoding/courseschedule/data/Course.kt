package com.dicoding.courseschedule.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.courseschedule.data.DataCourseName.COL_COURSE_NAME
import com.dicoding.courseschedule.data.DataCourseName.COL_DAY
import com.dicoding.courseschedule.data.DataCourseName.COL_END_TIME
import com.dicoding.courseschedule.data.DataCourseName.COL_ID
import com.dicoding.courseschedule.data.DataCourseName.COL_LECTURER
import com.dicoding.courseschedule.data.DataCourseName.COL_NOTE
import com.dicoding.courseschedule.data.DataCourseName.COL_START_TIME

//TODO 1 : Define a local database table using the schema in app/schema/course.json
@Entity(tableName = DataCourseName.TABLE_NAME)
data class Course(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,
    val courseName: String,
    val day: Int,
    val startTime: String,
    val endTime: String,
    val lecturer: String,
    val note: String
)
