xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
declare variable $METADATA_FULLNAME_KEY := xs:anyURI("http://axschema.org/namePerson");
declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI("http://exist-db.org/security/description");
if(xmldb:login("/db/","admin","")) then
    (
        <users>
            {for $user in sm:list-users()return
                (
                <user>
                    <username>{$user}</username>
                    <primaryGroups>{sm:get-user-primary-group($user)}</primaryGroups>
                    <fullName>{sm:get-account-metadata($user, $METADATA_FULLNAME_KEY)}</fullName>
                    <groups>
                        {for $group in sm:get-user-groups($user) return(<group>{$group}</group>)}
                    </groups>
                    <umask>{sm:get-umask($user)}</umask>
                    <desc>{sm:get-account-metadata($user, $METADATA_DESCRIPTION_KEY)}</desc>
                    <isEnabled>{sm:is-account-enabled($user)}</isEnabled>
                </user>
                )
            }
        </users>
    )
else
false()