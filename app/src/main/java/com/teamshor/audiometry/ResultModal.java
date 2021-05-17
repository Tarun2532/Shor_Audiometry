package com.teamshor.audiometry;


public class ResultModal {

        // variables for our course
        // name and description.
        private String time;
        private String date;

        // creating constructor for our variables.
        public ResultModal(String time, String date) {
            this.time = time;
            this.date = date;
        }

        // creating getter and setter methods.
        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
