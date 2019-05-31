cmake_minimum_required(VERSION 3.4.1)
add_library( # Sets the name of the library.
        gif-lib
        SHARED
        src/main/cpp/dgif_lib.c
        src/main/cpp/gifalloc.c
        src/main/cpp/gif-lib.cpp
        )

find_library(
        jnigraphics-lib
        jnigraphics )
find_library( # Sets the name of the path variable.
        log-lib
        log
        )

target_link_libraries(
        gif-lib
        ${log-lib}
        ${jnigraphics-lib}
)