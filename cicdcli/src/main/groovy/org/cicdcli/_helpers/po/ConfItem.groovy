package org.cicdcli._helpers.po

class ConfItem<T> {
    boolean isSecret
    String key
    T value
    Class type

    @Override
    boolean equals(Object confItem){
        if(
            this.is(confItem)
            ||
            confItem.class != ConfItem
        ){
            return false
        }

        return (
            this.isSecret == confItem.isSecret
            &&
            this.key == confItem.key
            &&
            this.value == confItem.value
            &&
            this.type == confItem.type
        )
    }
}
