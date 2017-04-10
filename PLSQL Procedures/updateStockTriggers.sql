select * from stocks;

desc stockstrigger;

create or replace procedure updateStockTriggers(vsymbol stocks.symbol%type, vbuyprice stockstrigger.buyprice%type, 
    vstoploss stockstrigger.stoploss%type, vprofitbook stockstrigger.profitbook%type, vstatus stockstrigger.status%type) 
is
    cursor c1 is select * from stockstrigger where symbol = vsymbol;
    stockrec stockstrigger%rowtype;
begin
    open c1;
    fetch c1 into stockrec;
    if c1%found then
         update stockstrigger set buyprice = vbuyprice, stoploss = vstoploss, vprofitbook = vprofitbook where symbol = vsymbol;
         dbms_output.put_line('IN UPDATE');
    else
        insert into stockstrigger values (vsymbol, vbuyprice, vstoploss, vprofitbook, vstatus);
    end if;
end;

show errors;

set serveroutput on;