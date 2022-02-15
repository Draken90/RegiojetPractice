package webtest.base

import org.openqa.selenium.By
import webtest.exceptions.InvalidElementDefException

class ElementDef {

    val componentType: ComponentType
    val selector: By
    val userFriendlyName: String

    constructor(componentType: ComponentType, userFriendlyName: String, selector: By) {
        this.componentType = componentType
        this.userFriendlyName = userFriendlyName
        this.selector = selector
    }

    /**
     * This ctor gets user-friendly name from selector
     * Use only if is Searched by text() otherwise it will throw [InvalidElementDefException]
     */
    constructor(componentType: ComponentType, selector: By) {
        if (!selector.toString().contains("text()='")){
            throw InvalidElementDefException("Byl zvolen špatný kontruktor ElemendDefu, tento se používá pouze při vyhledávání podle textu.")
        }
        this.componentType = componentType
        this.userFriendlyName = selector.toString().substringAfter("text()='").substringBefore("']")
        this.selector = selector
    }

    constructor(componentType: ComponentType,userFriendlyName: String,id: String) {
        this.componentType = componentType
        this.userFriendlyName = userFriendlyName
        this.selector = By.id(id)
    }
}