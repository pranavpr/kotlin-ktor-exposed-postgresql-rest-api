package net.pranavprakash.app.controller

import org.jetbrains.exposed.sql.transactions.transaction
import net.pranavprakash.app.dao.Messages
import net.pranavprakash.app.model.Message
import org.jetbrains.exposed.sql.*

class MessagesController {

    fun index(): ArrayList<Message> {
        val messages: ArrayList<Message> = arrayListOf()
        transaction {
            Messages.selectAll().map { messages.add(Message(id = it[Messages.id], text = it[Messages.text])) }
        }
        return messages
    }

    fun create(message: Message): Message {
        val messageId = transaction {
            Messages.insert {
                it[Messages.text] = message.text
            }.generatedKey
        }
        return message.copy(id = messageId!!.toInt())
    }

    fun show(id: Int): Message {
        return transaction {
            Messages.select { Messages.id eq id }
                    .map { Message(id = it[Messages.id], text = it[Messages.text]) }
                    .first()
        }
    }

    fun update(id: Int, newMessage: Message): Message {
        transaction {
            Messages.update({ Messages.id eq id }) {
                it[text] = newMessage.text
            }
        }
        return Message(id = id, text = newMessage.text)
    }

    fun delete(id: Int) {
        transaction {
            Messages.deleteWhere { Messages.id eq id }
        }
    }

}