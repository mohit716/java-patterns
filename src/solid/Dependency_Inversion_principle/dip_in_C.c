/*
 
Dependency Inversion means:

high-level code should not directly depend on low-level code
Instead, both depend on an abstraction.

In C, since there are no interfaces/classes, we usually do this using:

struct

function pointer

Without Dependency Inversion

Here the high-level function directly depends on a specific low-level function.

*/

#include <stdio.h>

void print_to_console(const char* msg) {
    printf("Console: %s\n", msg);
}

void notify(const char* msg) {
    // high-level code directly depends on console output
    print_to_console(msg);
}

int main() {
    notify("Hello");
    return 0;
}

/*
 
   otify() is tightly coupled to print_to_console().

So later if you want:

file output

network output

log output

you must change notify().

That is the problem.

With Dependency Inversion

Now we make an abstraction using a function pointer.

*/

#include <stdio.h>

/* Step 1:
   Make an abstraction.
   In C, this is often a struct holding function pointers.
*/
typedef struct {
    void (*send)(const char* msg);
} MessageSender;

/* Step 2:
   Low-level module 1
*/
void console_send(const char* msg) {
    printf("Console: %s\n", msg);
}

/* Step 3:
   Low-level module 2
*/
void file_send(const char* msg) {
    printf("File: %s\n", msg);
}

/* Step 4:
   High-level module depends only on abstraction,
   not on console_send or file_send directly.
*/
void notify(MessageSender* sender, const char* msg) {
    sender->send(msg);
}

int main() {
    MessageSender console = { console_send };
    MessageSender file = { file_send };

    notify(&console, "Hello");
    notify(&file, "Hi");

    return 0;
}

/*
  
Why this is Dependency Inversion
Bad version

notify() depends on print_to_console()

Good version

notify() depends on MessageSender

And console_send() / file_send() are plugged in from outside.

So:

high-level module = notify

low-level modules = console_send, file_send

abstraction = MessageSender

*/


