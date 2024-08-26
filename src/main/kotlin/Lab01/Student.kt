package Lab01

class Student(name: String, age: Int) : Human(name, age) {
    val courses = mutableListOf<CourseRecord>()

    fun addCourse(course: CourseRecord) {
        courses.add(course)
    }

    fun weightedAverage(): Double {
        if (courses.isEmpty()) return 0.0

        val totalWeightedGrades = courses.sumByDouble { it.grade * it.credits }
        val totalCredits = courses.sumBy { it.credits }
        return if (totalCredits == 0) 0.0 else totalWeightedGrades / totalCredits
    }

    fun weightedAverage(year: Int): Double {
        val filteredCourses = courses.filter { it.yearCompleted == year }
        if (filteredCourses.isEmpty()) return 0.0

        val totalWeightedGrades = filteredCourses.sumByDouble { it.grade * it.credits }
        val totalCredits = filteredCourses.sumBy { it.credits }
        return if (totalCredits == 0) 0.0 else totalWeightedGrades / totalCredits
    }

    fun minMaxGrades(): Pair<Double, Double> {
        if (courses.isEmpty()) return 0.0 to 0.0

        val minGrade = courses.minOfOrNull { it.grade } ?: 0.0
        val maxGrade = courses.maxOfOrNull { it.grade } ?: 0.0
        return minGrade to maxGrade
    }
}
