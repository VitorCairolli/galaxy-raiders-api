package galaxyraiders.core.physics

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlin.math.*
import kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType

@JsonIgnoreProperties("unit", "normal", "degree", "magnitude")
data class Vector2D(val dx: Double, val dy: Double) {
  override fun toString(): String {
    return "Vector2D(dx=$dx, dy=$dy)"
  }

  val magnitude: Double
    get() = sqrt(this.dx.pow(2) + this.dy.pow(2))

  val radiant: Double
    get(){
      val point = Point2D(this.unit.dx, this.unit.dy)
      val sign = sign(this.unit.dy)
      val size = point.distance(Point2D(1.0, 0.0))

      return sign * acos(1 - size.pow(2) / 2.0)
    }

  val degree: Double
    get() = this.radiant * 180/ PI

  val unit: Vector2D
    get() = this / this.magnitude

  val normal: Vector2D
    get() = Vector2D(this.unit.dy, - this.unit.dx)

  operator fun times(scalar: Double): Vector2D {
    return Vector2D(this.dx * scalar, this.dy * scalar)
  }

  operator fun div(scalar: Double): Vector2D {
    return Vector2D(this.dx / scalar, this.dy / scalar)
  }

  operator fun times(v: Vector2D): Double {
    return (this.dx * v.dx) + (this.dy * v.dy)
  }

  operator fun plus(v: Vector2D): Vector2D {
    return Vector2D(v.dx + this.dx, v.dy + this.dy)
  }

  operator fun plus(p: Point2D): Point2D {
    return Point2D(this.dx + p.x, this.dy + p.y)
  }

  operator fun unaryMinus(): Vector2D {
    return Vector2D(-this.dx, -this.dy)
  }

  operator fun minus(v: Vector2D): Vector2D {
    return Vector2D(this.dx - v.dx, this.dy - v.dy)
  }

  fun scalarProject(target: Vector2D): Double {
    return this * target.unit
  }

  fun vectorProject(target: Vector2D): Vector2D {
    return scalarProject(target) * target.unit
  }
}

operator fun Double.times(v: Vector2D): Vector2D {
  return Vector2D(v.dx * this, v.dy * this)
}
