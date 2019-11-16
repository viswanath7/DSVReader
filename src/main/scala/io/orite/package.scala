package io

package object orite {
	
	import shapeless._
	import shapeless.record._
	import shapeless.ops.hlist.ToList
	import shapeless.ops.record.{Keys, ToMap}
	
	implicit class ProductOps[CC <: Product](caseClazz: CC) {
		
		def toMap[Repr <: HList](implicit generic: LabelledGeneric.Aux[CC, Repr], toMap: ToMap[Repr]): Map[String, toMap.Value] =
			toMap(generic.to(caseClazz))
				.map { entry =>
					(entry._1 match {
						case symbol: Symbol => symbol.name
						case _ => entry._1.toString
					}, entry._2)
				}
		
		def fieldNames[Repr <: HList, K <: HList](implicit labelledGeneric: LabelledGeneric.Aux[CC, Repr], keys: Keys.Aux[Repr, K], ktl: ToList[K, Symbol]): List[String] =
			labelledGeneric.to(caseClazz).keys.toList.map(_.name)
	}
}
