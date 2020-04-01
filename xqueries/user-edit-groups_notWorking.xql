xquery version "3.1";
import module namespace sm="http://exist-db.org/xquery/securitymanager";
declare variable $groups := ["eXide", "guest", "monex"];
if(xmldb:login("/db/","admin","admin1234")) then
    (
        for $group in ["eXide", "guest", "monex"] return(sm:remove-group-member($group, "kaeom0914")),
        for $group in $groups return if(sm:group-exists($group)) then sm:add-group-member($group, "kaeom0914") else (),
        true()
    )
    else
    false()