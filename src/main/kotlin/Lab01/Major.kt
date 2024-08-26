package Lab01

class Major(val name: String) {
    private val students = mutableListOf<Student>()

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun stats(): Triple<Double, Double, Double> {
        if (students.isEmpty()) return Triple(0.0, 0.0, 0.0)

        // Calculate averages for all students
        val averages = students.map { it.weightedAverage() }

        val minAvg = averages.minOrNull() ?: 0.0
        val maxAvg = averages.maxOrNull() ?: 0.0
        val avgOfAverages = averages.average()

        return Triple(minAvg, maxAvg, avgOfAverages)
    }

    fun stats(courseName: String): Triple<Double, Double, Double> {
        // Collect all grades for the specified course
        val courseGrades = mutableListOf<Double>()

        for (student in students) {
            val gradesForCourse = student.courses
                .filter { it.name == courseName }
                .map { it.grade }
            courseGrades.addAll(gradesForCourse)
        }

        if (courseGrades.isEmpty()) return Triple(0.0, 0.0, 0.0)

        // Calculate minimum, maximum, and average grades
        val minGrade = courseGrades.minOrNull() ?: 0.0
        val maxGrade = courseGrades.maxOrNull() ?: 0.0
        val avgGrade = courseGrades.average()

        return Triple(minGrade, maxGrade, avgGrade)
    }
}
