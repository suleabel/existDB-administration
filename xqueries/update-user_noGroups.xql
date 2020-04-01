xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
declare variable $METADATA_FULLNAME_KEY := xs:anyURI("http://axschema.org/namePerson");
declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI("http://exist-db.org/security/description");
if(xmldb:login("/db/","admin","admin1234")) then
    (
        let $user := "kaeom0914",
        $fullName := "valtoztatott",
        $description := "valtoztatott",
        $password := "valtoztatott",
        $primaryGroup := "dba",
        $disabled := xs:boolean("false"),
        $umask := xs:integer(22)
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
        if(sm:get-user-primary-group($user) ne $primaryGroup)then
            sm:set-user-primary-group($user, $primaryGroup)
        else(),
        if(sm:get-umask($user) ne $umask)then
            sm:set-umask($user, $umask)
        else(),
        if($password ne "null") then sm:passwd($user, $password) else (),
            true()
        )
    )
else
false()