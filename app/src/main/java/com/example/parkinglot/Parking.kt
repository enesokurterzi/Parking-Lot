package com.example.parkinglot

class Parking {
    private var size:Int = 0
    private var parkingSlot = mutableListOf<Boolean>()
    private var carRegistrationNumber = mutableListOf<String>()
    private var carColor = mutableListOf<String>()

    fun starter(){
        val request = readln().split(" ").map { it }
        if (size == 0) {
            if (request[0] == "create") {
                create(request[1].toInt())
            } else if (request[0] == "exit") {
                return
            } else {
                println("Sorry, a parking lot has not been created.")
                starter()
            }
        } else if (request[0] == "create") {
            create(request[1].toInt())
        } else {
            processRequest(request)
        }
    }

    private fun processRequest(request: List<String>) {
        when (request[0]) {
            "park" -> park(request[1], request[2])
            "leave" -> leave(request[1].toInt())
            "status" -> status()
            "reg_by_color" -> regByColor(request[1])
            "spot_by_color" -> spotByColor(request[1])
            "spot_by_reg" -> spotByReg(request[1])
            "exit" -> return
        }
        starter()
    }

    private fun park(plate: String, color: String) {
        var parkFinder = false
        for (i in 0 until size) {
            if (parkingSlot[i]) {
                parkFinder = true
                parkingSlot[i] = false
                carRegistrationNumber[i] = plate
                carColor[i] = color
                println("$color car parked in spot ${i + 1}.")
                break
            }
        }
        if (!parkFinder) {
            println("Sorry, the parking lot is full.")
        }
    }

    private fun leave(spot: Int) {
        when (parkingSlot[spot - 1]) {
            true -> println("There is no car in spot $spot.")
            false -> {
                parkingSlot[spot - 1] = true
                carRegistrationNumber[spot - 1] = ""
                carColor[spot - 1]= ""
                println("Spot $spot is free.")
            }
        }
    }

    private fun status() {
        var isThereAnyCar = false
        for (i in 0 until size){
            if (!parkingSlot[i]) {
                isThereAnyCar = true
                println("${i+1} ${carRegistrationNumber[i]} ${carColor[i]}")
            }
        }
        if (!isThereAnyCar) {
            println("Parking lot is empty.")
        }
    }

    private fun regByColor(regByThisColor: String) {
        var isThereAnyCarWithThisColor = false
        for (i in 0 until size){
            if (carColor[i].equals(regByThisColor, ignoreCase = true)) {
                if (!isThereAnyCarWithThisColor) {
                    print(carRegistrationNumber[i])
                } else {
                    print(", ${carRegistrationNumber[i]}")
                }
                isThereAnyCarWithThisColor = true
            }
        }
        if (!isThereAnyCarWithThisColor) print("No cars with color $regByThisColor were found.")
        println("")
    }

    private fun spotByColor(spotByThisColor: String) {
        var isThereAnyCarWithThisColor = false
        for (i in 0 until size){
            if (carColor[i].equals(spotByThisColor, ignoreCase = true)) {
                if (!isThereAnyCarWithThisColor) {
                    print(i+1)
                } else {
                    print(", ${i+1}")
                }
                isThereAnyCarWithThisColor = true
            }
        }
        if (!isThereAnyCarWithThisColor) print("No cars with color $spotByThisColor were found.")
        println("")
    }

    private fun spotByReg(spotByThisReg: String) {
        var isThereAnyCarWithThisReg = false
        for (i in 0 until size){
            if (carRegistrationNumber[i].equals(spotByThisReg, ignoreCase = true)) {
                if (!isThereAnyCarWithThisReg) {
                    print(i+1)
                } else {
                    print(", ${i+1}")
                }
                isThereAnyCarWithThisReg = true
            }
        }
        if (!isThereAnyCarWithThisReg) print("No cars with registration number $spotByThisReg were found.")
        println("")
    }

    private fun create(givenSize: Int) {
        size = givenSize
        parkingSlot = MutableList(size) {true}
        carRegistrationNumber = MutableList(size) {""}
        carColor = MutableList(size) {""}
        println("Created a parking lot with $size spots.")
        starter()
    }
}