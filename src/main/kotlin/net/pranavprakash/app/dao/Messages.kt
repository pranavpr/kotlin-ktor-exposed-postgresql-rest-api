package net.pranavprakash.app.dao

import org.jetbrains.exposed.sql.*

object Messages : Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val text = text("text")
}