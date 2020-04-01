xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
declare variable $METADATA_FULLNAME_KEY := xs:anyURI("http://axschema.org/namePerson");
declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI("http://exist-db.org/security/description");
if(xmldb:login("/db/","admin","admin1234")) then
    (
        let $user := "",
        $fullName := "",
        $description := "",
        $password := "",
        $disabled := false,
        $umask := "",
        $groups := [][@type eq "array"]/item/string(text())
        return (
            if(secman:get-account-metadata($user, $METADATA_FULLNAME_KEY) = $fullName)then
                    ()
                else secman:set-account-metadata($user, $METADATA_FULLNAME_KEY, $fullName)
                ,
            if(secman:get-account-metadata($user, $METADATA_DESCRIPTION_KEY) = $description)then
                    ()
                else secman:set-account-metadata($user, $METADATA_DESCRIPTION_KEY, $description)
                ,
            if(secman:is-account-enabled($user) eq $disabled)then
                    secman:set-account-enabled($user, $disabled)
                else(),
            if(secman:get-umask($user) ne $umask)then
                    secman:set-umask($user, $umask)
                else(),
                for $group in secman:get-user-groups($user) return secman:remove-group-member($group, $user),
                for $group in $groups return if(secman:group-exists($group)) then secman:add-group-member($group, $user) else (),
                if($password) then secman:passwd($user, $password) else (),

                true()
            )
    )
else
false()