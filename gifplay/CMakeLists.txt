cmake_minimum_required(VERSION 3.4.1)
add_library( # Sets the name of the library.
        gif-lib
        SHARED
        src/main/cpp/gif-lib.cpp
        )

find_library( # Sets the name of the path variable.
        log-lib
        log)
target_link_libraries(
        gif-lib
        ${log-lib}
)