xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
declare variable $METADATA_FULLNAME_KEY := xs:anyURI("http://axschema.org/namePerson");
declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI("http://exist-db.org/security/description");
declare variable $username := 'testUSer';
if(xmldb:login("/db/","admin","admin1234")) then
    (
                <user>
                    <username>{$username}</username>
                    <primaryGroups>{sm:get-user-primary-group($username)}</primaryGroups>
                    <fullName>{sm:get-account-metadata($username, $METADATA_FULLNAME_KEY)}</fullName>
                    
                        {for $group in sm:get-user-groups($username) return(<group>{$group}</group>)}
                    
                    <umask>{sm:get-umask($username)}</umask>
                    <desc>{sm:get-account-metadata($username, $METADATA_DESCRIPTION_KEY)}</desc>
                    <isEnabled>{sm:is-account-enabled($username)}</isEnabled>
                </user>
    )
else
false()