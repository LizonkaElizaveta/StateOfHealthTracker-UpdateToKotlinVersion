package stanevich.elizaveta.stateofhealthtracker.database

class DbSchema {

    object MainActivityTable {
        val TABLE_NAME = "MainActivity"
        val ID = "_id"
        val DATE = "date"
        val TIME = "time"
        val MOOD = "flag"
        val PILL = "pill"
        val DISKINEZIA = "diskinezia"
    }

    object NotificationActivityTable {
        val TABLE_NAME = "NotificationActivity"
        val ID = "_id"
        val TIME = "time"
        val NAME_MEDICATION = "medication"
    }

    object AuthActivityTable {
        val TABLE_NAME = "AuthActivity"
        val ID = "_id"
        val FIRST_NAME = "firstName"
        val LAST_NAME = "lastName"
        val PHONE = "phone"
        val MAIL = "mail"

    }
}