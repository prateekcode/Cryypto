package com.prateekcode.cryypto.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prateekcode.cryypto.utils.CRYYPTO_TABLE
import kotlinx.parcelize.Parcelize

@Entity(tableName = CRYYPTO_TABLE)
@Parcelize
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val currencyId: String
) : Parcelable