create table stockprice
(
    symbol varchar(20) references stocks(symbol) unique,
    price number,
    lastupdate timestamp    
);

create or replace procedure updateStockPrice(vsymbol stockprice.symbol%type, vprice stockprice.price%type, vdate stockprice.lastupdate%type)
is
    cursor c1 is select * from stockprice where symbol = vsymbol;
    stockrec stockprice%rowtype;
begin
    open c1;
    fetch c1 into stockrec;
    if c1%found then
        update stockprice set price = vprice, lastupdate = vdate where symbol = vsymbol;
        dbms_output.put_line('IN UPDATE');
    else
        insert into stockprice values(vsymbol, vprice, vdate);
        dbms_output.put_line('IN INSERT');
    end if;
end;

show errors;


//jobin
