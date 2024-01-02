import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatCard',
  standalone: true
})
export class FormatCardPipe implements PipeTransform {

  transform(value: string): string {
    // assumes exactly 16 digits for credit card number
    let out: string = "";
    for(let i = 0; i < value.length - 4; i += 4) {
      out += value.substring(i, i + 4) + '-';
    }
    out += value.substring(12);
    return out;
  }

}
