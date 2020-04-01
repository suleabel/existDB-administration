xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
declare variable $METADATA_FULLNAME_KEY := xs:anyURI("http://axschema.org/namePerson");
declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI("http://exist-db.org/security/description");
if(xmldb:login("/db/","admin","admin1234")) then
    (
        let $user := "kaeom0914",
        $fullName := "asd",
        $description := "asasd",
        $password := "null",
        $disabled := xs:boolean("true"),
        $umask := xs:integer(18),
        $groups := ["eXide", "guest", "monex"]
        return (
            if(sm:get-account-metadata($user, $METADATA_FULLNAME_KEY) = $fullName)then
        ()else 
            sm:set-account-metadata($user, $METADATA_FULLNAME_KEY, $fullName),
        if(sm:get-account-metadata($user, $METADATA_DESCRIPTION_KEY) = $description)then
        ()else 
            sm:set-account-metadata($user, $METADATA_DESCRIPTION_KEY, $description),
        if(sm:is-account-enabled($user) eq $disabled)then
            sm:set-account-enabled($user, $disabled)
        else(),

                if(sm:get-umask($user) ne $umask)then
                    sm:set-umask($user, $umask)
                else(),

                (: if a password is provided, we always change the password, as we dont know what the original password was :)
                
                for $group in ["eXide", "guest", "monex"] return(
                    sm:remove-group-member($group, "kaeom0914")),

                (: success :)
                true()
            )
    )
else
false()