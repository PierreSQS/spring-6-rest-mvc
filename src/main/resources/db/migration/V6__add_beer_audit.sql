create table beer_audit (
      auditid varchar(36) not null, -- according to Pierrot BeerAudit
      id varchar(36) not null,
      beer_name varchar(50) not null,
      beer_style tinyint not null,
      created_date datetime(6),
      price decimal(38,2) not null,
      quantity_on_hand integer,
      upc varchar(255) not null,
      update_date datetime(6),
      version integer,
      created_date_audit datetime(6),
      principal_name varchar(255),
      audit_event_type varchar(255),
      primary key (auditid)
) engine=InnoDB;