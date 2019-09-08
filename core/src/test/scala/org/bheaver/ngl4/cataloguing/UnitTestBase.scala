package org.bheaver.ngl4.cataloguing

import org.scalamock.scalatest.{AsyncMockFactory, MockFactory}
import org.scalatest._

abstract class UnitTestBase  extends FlatSpec with Matchers with OptionValues with Inside with Inspectors with MockFactory

abstract class AsyncUnitTestBase  extends AsyncFlatSpec with Matchers with OptionValues with Inside with Inspectors with AsyncMockFactory
