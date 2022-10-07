package com.raeed.tictactoc.sideClasses

class Frinds {
      var user_name: String? = null
      var user_Id: String? = null
      var last_Date: String? = null
      var last_Time: String? = null
      var currentStates: String? = null

      internal constructor() {}
      constructor(
            user_name: String?,
            user_Id: String?,
            last_Date: String?,
            last_Time: String?,
            currentStates: String?
      ) {
            this.user_name = user_name
            this.user_Id = user_Id
            this.last_Date = last_Date
            this.last_Time = last_Time
            this.currentStates = currentStates
      }
}