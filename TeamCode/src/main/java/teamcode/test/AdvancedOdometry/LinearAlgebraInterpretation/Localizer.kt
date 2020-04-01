package teamcode.test.AdvancedOdometry.LinearAlgebraInterpretation

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.kinematics.Kinematics
import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.DecompositionSolver
import org.apache.commons.math3.linear.LUDecomposition
import org.apache.commons.math3.linear.MatrixUtils

abstract class Localizer (wheelPoses: List<Pose2d>) {

    private var _poseEstimate = Pose2d()
    private var lastWheelPositions = emptyList<Double>()

    private val forwardSolver: DecompositionSolver


    init {
        require(wheelPoses.size == 3) { "3 wheel positions must be provided" }

        val inverseMatrix = Array2DRowRealMatrix(3, 3)
        for (i in 0..2) {
            val orientationVector = wheelPoses[i].headingVec()
            val positionVector = wheelPoses[i].vec()
            inverseMatrix.setEntry(i, 0, orientationVector.x)
            inverseMatrix.setEntry(i, 1, orientationVector.y)
            inverseMatrix.setEntry(i, 2,
                    positionVector.x * orientationVector.y - positionVector.y * orientationVector.x)
        }

        forwardSolver = LUDecomposition(inverseMatrix).solver

        require(forwardSolver.isNonSingular) { "The specified configuration cannot support full localization" }
        fun update() {
            val wheelPositions = getWheelPositions()
            if (lastWheelPositions.isNotEmpty()) {
                val wheelDeltas = wheelPositions
                        .zip(lastWheelPositions)
                        .map { it.first - it.second }
                val rawPoseDelta = forwardSolver.solve(MatrixUtils.createRealMatrix(
                        arrayOf(wheelDeltas.toDoubleArray())
                ).transpose())
                val robotPoseDelta = Pose2d(
                        rawPoseDelta.getEntry(0, 0),
                        rawPoseDelta.getEntry(1, 0),
                        rawPoseDelta.getEntry(2, 0)
                )
                _poseEstimate = Kinematics.relativeOdometryUpdate(_poseEstimate, robotPoseDelta)
            }
            lastWheelPositions = wheelPositions
        }

    }
    abstract fun getWheelPositions(): List<Double>


}