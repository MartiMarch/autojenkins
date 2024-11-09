/* TODO: Descubrir como hacer un Mock del System....

package org.cicdcli._helpers


import spock.lang.Specification

class EnvsTest extends Specification {


    @Unroll
    def 'getEnv must return expected environment variable as ConfigItem'(){
        given:
        with(customSystem){
            getEnvValue(env) >> value
        }

        when:
        ConfItem confItem = Envs.getEnv(key, false, nrClass)

        then:
        confItem == new ConfItem(
            key: key,
            isSecret: false,
            type: nrClass,
            value: value
        )

        where:
        key           | value        | env                     | nrClass
        'fake.env'    | 'fake_value' | 'CICDCLI_FAKE_ENV'      | String
        'fake.number' | '12312'      | 'CICDCLI_RANDOM_NUMBER' | Integer
    }

}
*/
