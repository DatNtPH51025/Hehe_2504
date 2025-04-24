package com.poly.thi_ph51025

import com.google.gson.annotations.SerializedName

data class MonThiResponse(
    @SerializedName("id") val id: String,
    @SerializedName("hoTen") val hoTen: String,
    @SerializedName("monThi") val monThi: String,
    @SerializedName("hinhAnh") val hinhAnh: String,
    @SerializedName("ngayThi") val ngayThi: String,
    @SerializedName("caThi") val caThi: String,
)

fun MonThiResponse.toMonThi(): MonThi {
    return MonThi(
        id = this.id,
        hoTen = this.hoTen,
        monThi = this.monThi,
        ngayThi = this.ngayThi,
        hinhAnh = this.hinhAnh,
        caThi = this.caThi,
    )
}
//b2