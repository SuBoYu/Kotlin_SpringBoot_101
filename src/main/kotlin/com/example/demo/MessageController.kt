package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
//import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URI

//@RestController
//class MessageController {
//    @GetMapping("/")
//    fun index(@RequestParam("name") name: String) = "Hello, $name!"
//
//    @GetMapping("/list_of_messages")
//    fun listMessage() = listOf(
//        Message(id = "1", text = "Hello"),
//        Message(id = "2", text = "World"),
//        Message(id = "3", text = ".")
//    )
//}
@RestController
@RequestMapping("/")
class MessageController(private val service: MessageService) {
    @GetMapping
    fun listMessages() = service.findMessages()

    @PostMapping
    fun post(@RequestBody message: Message): ResponseEntity<Message> {
        val savedMessage = service.save(message)
        return ResponseEntity.created(URI("/${savedMessage.id}")).body(savedMessage)
    }

    @GetMapping("/{id}")
    fun getMessage(@PathVariable("id") id: String): ResponseEntity<Message> =
        service.findMessageById(id).toResponseEntity()
    private fun Message?.toResponseEntity(): ResponseEntity<Message> =
        // If the message is null (not found), set response code to 404
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

}