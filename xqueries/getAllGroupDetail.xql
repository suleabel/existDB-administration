xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
declare variable $METADATA_DESCRIPTION_KEY := xs:anyURI("http://exist-db.org/security/description");
if(xmldb:login("/db/","admin","")) then
    (
        <groups>
            {for $group in sm:list-groups()return
                (
                <group>
                    <name>{$group}</name>
                    <manager>{sm:get-group-managers($group)}</manager>
                    <desc>{sm:get-group-metadata($group, $METADATA_DESCRIPTION_KEY)}</desc>
                    <groupMembers>
                        {for $member in sm:get-group-members($group)return
                            (
                                <member>{$member}</member>
                            )
                        }
                    </groupMembers>
                </group>
                )
            }
        </groups>
    )
else
false()